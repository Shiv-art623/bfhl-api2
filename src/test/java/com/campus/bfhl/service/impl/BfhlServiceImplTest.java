package com.campus.bfhl.service.impl;

import com.campus.bfhl.dto.BfhlRequest;
import com.campus.bfhl.dto.BfhlResponse;
import com.campus.bfhl.exception.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests verifying business rules of BfhlServiceImpl.
 */
class BfhlServiceImplTest {

    private BfhlServiceImpl bfhlService;

    @BeforeEach
    void setUp() {
        bfhlService = new BfhlServiceImpl();
        // Inject configuration property values using ReflectionTestUtils
        ReflectionTestUtils.setField(bfhlService, "userId", "shivani_prajapati_26052026");
        ReflectionTestUtils.setField(bfhlService, "email", "shivani.prajapati@university.edu");
        ReflectionTestUtils.setField(bfhlService, "rollNumber", "CU12345678");
    }

    @Test
    void testProcessData_SuccessWithMixedInput() {
        // Input matching user prompt: ["a", "1", "334", "4", "R", "$"]
        List<String> data = Arrays.asList("a", "1", "334", "4", "R", "$");
        BfhlRequest request = new BfhlRequest(data);

        BfhlResponse response = bfhlService.processData(request);

        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("shivani_prajapati_26052026", response.getUserId());
        assertEquals("shivani.prajapati@university.edu", response.getEmail());
        assertEquals("CU12345678", response.getRollNumber());

        // Odd numbers: "1"
        assertEquals(1, response.getOddNumbers().size());
        assertEquals("1", response.getOddNumbers().get(0));

        // Even numbers: "334", "4"
        assertEquals(2, response.getEvenNumbers().size());
        assertTrue(response.getEvenNumbers().containsAll(Arrays.asList("334", "4")));

        // Alphabets: "A", "R" (converted to uppercase)
        assertEquals(2, response.getAlphabets().size());
        assertEquals("A", response.getAlphabets().get(0));
        assertEquals("R", response.getAlphabets().get(1));

        // Special characters: "$"
        assertEquals(1, response.getSpecialCharacters().size());
        assertEquals("$", response.getSpecialCharacters().get(0));

        // Sum: 1 + 334 + 4 = 339
        assertEquals(339, response.getSum());

        // Concat string: "a" and "R" concatenated -> "aR", reversed -> "Ra",
        // alternating caps -> "Ra"
        assertEquals("Ra", response.getConcatString());
    }

    @Test
    void testProcessData_SuccessWithRequirement18SampleInput() {
        // Input matching Requirement 18: ["2","a","y","4","&","-","*","5","92","b"]
        List<String> data = Arrays.asList("2", "a", "y", "4", "&", "-", "*", "5", "92", "b");
        BfhlRequest request = new BfhlRequest(data);

        BfhlResponse response = bfhlService.processData(request);

        assertNotNull(response);
        assertTrue(response.isSuccess());

        // Odd numbers: "5"
        assertEquals(1, response.getOddNumbers().size());
        assertEquals("5", response.getOddNumbers().get(0));

        // Even numbers: "2", "4", "92"
        assertEquals(3, response.getEvenNumbers().size());
        assertEquals(Arrays.asList("2", "4", "92"), response.getEvenNumbers());

        // Alphabets: "A", "Y", "B" (converted to uppercase)
        assertEquals(3, response.getAlphabets().size());
        assertEquals(Arrays.asList("A", "Y", "B"), response.getAlphabets());

        // Special characters: "&", "-", "*"
        assertEquals(3, response.getSpecialCharacters().size());
        assertEquals(Arrays.asList("&", "-", "*"), response.getSpecialCharacters());

        // Sum: 2 + 4 + 5 + 92 = 103
        assertEquals(103, response.getSum());

        // Concat string: alphabets "ayb" concatenated, reversed -> "bya", alternating
        // caps -> "ByA"
        assertEquals("ByA", response.getConcatString());
    }

    @Test
    void testProcessData_SuccessWithMultiCharAlphabets() {
        // Test multi-char alphabets rule: Input: ["A","ABCD","DOE"]
        List<String> data = Arrays.asList("A", "ABCD", "DOE");
        BfhlRequest request = new BfhlRequest(data);

        BfhlResponse response = bfhlService.processData(request);

        assertNotNull(response);
        assertTrue(response.isSuccess());

        // Alphabets uppercase: ["A", "ABCD", "DOE"]
        assertEquals(Arrays.asList("A", "ABCD", "DOE"), response.getAlphabets());

        // Concat string: concatenated "AABCDDOE", reversed -> "EODDCBAA", alternating
        // caps -> "EoDdCbAa"
        assertEquals("EoDdCbAa", response.getConcatString());
    }

    @Test
    void testProcessData_NullRequest_ThrowsException() {
        assertThrows(InvalidInputException.class, () -> bfhlService.processData(null));
    }

    @Test
    void testProcessData_NullDataList_ThrowsException() {
        BfhlRequest request = new BfhlRequest(null);
        assertThrows(InvalidInputException.class, () -> bfhlService.processData(request));
    }

    @Test
    void testProcessData_EmptyDataList_ThrowsException() {
        BfhlRequest request = new BfhlRequest(Collections.emptyList());
        assertThrows(InvalidInputException.class, () -> bfhlService.processData(request));
    }

    @Test
    void testProcessData_DataListWithNullElement_ThrowsException() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("a", null, "3"));
        assertThrows(InvalidInputException.class, () -> bfhlService.processData(request));
    }

    @Test
    void testProcessData_DataListWithBlankElement_ThrowsException() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("a", "  ", "3"));
        assertThrows(InvalidInputException.class, () -> bfhlService.processData(request));
    }
}
