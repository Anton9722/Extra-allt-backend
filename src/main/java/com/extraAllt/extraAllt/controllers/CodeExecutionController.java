package com.extraAllt.extraAllt.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/code-execution")
@CrossOrigin(origins = "*")
public class CodeExecutionController {

    @PostMapping("/run-code")
    public ResponseEntity<String> runCode(@RequestBody CodeRequest codeRequest) {
        try {
            // Skapa en temporär Java-fil
            String code = codeRequest.getCode();
            String expectedResult = codeRequest.getResultWeWant();
            File tempFile = File.createTempFile("UserCode", ".java");
            try (PrintWriter out = new PrintWriter(tempFile)) {
                out.println(code);
            }
    
            // Kompilera Java-koden
            Process compileProcess = new ProcessBuilder("javac", tempFile.getAbsolutePath())
                    .redirectErrorStream(true)
                    .start();
    
            // Fånga kompilatorns output
            StringBuilder compileOutput = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(compileProcess.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    compileOutput.append(line).append("\n");
                }
            }
    
            compileProcess.waitFor(); // Vänta på att kompileringen ska avslutas
    
            // Om kompileringen gick bra, kör programmet
            if (compileProcess.exitValue() == 0) {
                // Hämta klassnamnet som "UserCode" (eftersom vi definierar filnamnet som "UserCode")
                String className = "UserCode";
                Process runProcess = new ProcessBuilder("java", "-cp", tempFile.getParent(), className)
                        .redirectErrorStream(true)
                        .start();
    
                // Fånga output från processen
                StringBuilder output = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        output.append(line).append("\n");
                    }
                }
    
                runProcess.waitFor(); // Vänta tills programmet är klart
                
                if(output.toString().trim().equals(expectedResult)){
                    return ResponseEntity.ok("Correct!"); 
                } else{
                    return ResponseEntity.ok("Not Correct " + output.toString()); // Skicka tillbaka output
                }
            } else {
                // Fånga felmeddelanden om kompileringen misslyckades
                int errorIndex = compileOutput.toString().indexOf("error:");
                if(errorIndex != -1) {
                    String trimmedErrorMessage = compileOutput.toString().substring(errorIndex);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(trimmedErrorMessage);
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Syntax error:\n" + compileOutput.toString());
            }
        } catch (Exception e) {
            e.printStackTrace(); // Logga fel
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.toString());
        }
    }
    

    public static class CodeRequest {
        private String code;
        private String resultWeWant;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getResultWeWant() { 
            return resultWeWant;
        }
    
        public void setResultWeWant(String resultWeWant) {
            this.resultWeWant = resultWeWant;
        }
    }
}
