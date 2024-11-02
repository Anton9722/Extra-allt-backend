package com.extraAllt.extraAllt;

import com.extraAllt.extraAllt.controllers.CodeExecutionController;
import com.extraAllt.extraAllt.controllers.CodeExecutionController.CodeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodeExecutionControllerTests {

    private CodeExecutionController controller;

    @BeforeEach
    public void setUp() {
        controller = new CodeExecutionController();
    }

    @Test
    public void testRunCodeCorrectOutput() {
        CodeRequest codeRequest = new CodeRequest();
        codeRequest.setCode("class UserCode { public static void main(String[] args) { System.out.print(\"Test Hejsan\"); } }");
        codeRequest.setResultWeWant("Test Hejsan");

        ResponseEntity<String> response = controller.runCode(codeRequest);
        assertEquals("Correct!", response.getBody());
    }

    @Test
    public void testRunCodeIncorrectOutput() {
        CodeRequest codeRequest = new CodeRequest();
        codeRequest.setCode("class UserCode { public static void main(String[] args) { System.out.print(\"Hello Universe\"); } }");
        codeRequest.setResultWeWant("Hello World");

        ResponseEntity<String> response = controller.runCode(codeRequest);
        assertEquals("Not Correct", response.getBody());
    }

    @Test
    public void testRunCodeSyntaxError() {
        CodeRequest codeRequest = new CodeRequest();
        codeRequest.setCode("class UserCode { public static void main(String[] args) { System.out.print(Test Hej); } }");
        codeRequest.setResultWeWant("Test Hej");

        ResponseEntity<String> response = controller.runCode(codeRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(true, response.getBody().contains("error:"));
    }
}
