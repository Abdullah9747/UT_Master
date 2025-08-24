from testing import GenerateTestCasesJQF as GTCJQF
from testing import GenerateTestCasesSPF as GTCSPF
from testing import GenerateTestCasesLLM as GTCLLM
import testing
import pandas as pd
import re
import time

function1="""public class GETMASK {
/**
     * Calculate bit mask for a given number of bits. The mask should enable to
     * make a bitwise and to the given number of bits.
     * @param numOfBits number of bits to calculate mask for.
     * @return bit mask
     */
    public static int getMask(int numOfBits) {
        int mask = 0x00;

        switch (numOfBits) {
        case 1:
            mask = 0x01;
            break;
        case 2:
            mask = 0x03;
            break;
        case 3:
            mask = 0x07;
            break;
        case 4:
            mask = 0x0F;
            break;
        case 5:
            mask = 0x1F;
            break;
        case 6:
            mask = 0x3F;
            break;
        case 7:
            mask = 0x7F;
            break;
        case 8:
            mask = 0xFF;
            break;
        }
        return mask;
    }
}"""

javadoc, signature, body, full_method, class_name = testing.extract_java_method_parts(function1)
print("Full method:", full_method)
print("Signature:", signature)
print("Class:", class_name)

# Fix 1: Instantiate the class
newgen = GTCLLM()

# Fix 2: Pass signature first, then model, then other parameters
result = newgen.gen_TC_Gemini(signature, "gemini-2.0-flash", javadoc, body, class_name)
print(result)