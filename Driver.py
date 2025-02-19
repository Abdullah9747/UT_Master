from jqf import JQF
from spf import SPF
from LLMs import GenerateTestCasesJQF as GTCJQF
from LLMs import GenerateTestCasesSPF as GTCSPF
from LLMs import GenerateTestCasesLLM as GTCLLM
class Driver:
    def __init__(self):
        self.jqf=JQF()
        self.spf=SPF()
        self.GTCJQF=GTCJQF()
        self.GTCSPF=GTCSPF()
        self.GTCLLM=GTCLLM()

    def run_jqf(self,function_code):
        self.jqf.driverjqf(function_code)
        f1="E:/FYP/UT_Master/TestValGen/JQF-wsl/java-fuzzing-example/fuzz-results/plot_data"
        f2="E:/FYP/UT_Master/TestValGen/JQF-wsl/java-fuzzing-example/fuzz-results/fuzz.log"
        f4="gemini-1.5-flash"
        result=self.GTCJQF.gen_TC_Gemini(f1,f2,function_code,f4)
        print(result)


    def run_spf(self,function):
        result=self.spf.driverspf(function)
        f4="gemini-1.5-flash"
        result=self.GTCSPF.gen_TC_Gemini(result,function,f4)
        print(result)
    
    def run_LLM(self,function):
        f4="gemini-1.5-flash"
        result=self.GTCLLM.driver_LLM(function,f4)
        print(result)


obj=Driver()
function2="""public class CalculateTax {
    public static double calculateTax(double income, int dependents, boolean hasInvestments) {
        if (income < 0 || dependents < 0) {
            throw new IllegalArgumentException("Invalid input values");
        }
        double taxRate = income > 100000 ? 0.3 : income > 50000 ? 0.2 : 0.1;
        double baseTax = income * taxRate;
        if (dependents > 0) {
            baseTax -= dependents * 2000; // Deduction per dependent
        }
        if (hasInvestments) {
            baseTax *= 0.85; // Investment rebate
        }
        return Math.max(baseTax, 0); // Ensure tax is not negative
    }

}"""


obj.run_spf(function2)