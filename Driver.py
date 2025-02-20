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


function3="""public class ElectricityBill {
    public static double calculateBill(int unitsConsumed, double ratePerUnit, boolean isPeakMonth) {
        if (unitsConsumed < 0 || ratePerUnit <= 0) {
            throw new IllegalArgumentException("Invalid input values");
        }
        double bill = unitsConsumed * ratePerUnit;
        if (isPeakMonth) {
            bill *= 1.2; // Surcharge during peak months
        } 
        if (unitsConsumed > 500) {
            bill += 50; // Additional fixed charge for high usage
        } else if (unitsConsumed < 100) {
            bill *= 0.9; // Discount for low usage
        }
        return bill;
    }

}"""



function2="""public class WaterUsage {
    public static double calculateWaterUsage(int familyMembers, int appliances, boolean hasGarden, int dailyUseLiters) {
        double baseUsage = familyMembers * dailyUseLiters;
        if (hasGarden) {
            baseUsage += 50; // Additional for garden
        }
        if (appliances > 0) {
            baseUsage += appliances * 10; // Additional for appliances
        }
        if (familyMembers > 5) {
            baseUsage *= 1.1; // Slight increase for larger families
        }
        return baseUsage;
    }

}"""

obj.run_jqf(function2)