from jqf import JQF
from spf import SPF
from LLMs import GenerateTestCasesJQF as GTCJQF
from LLMs import GenerateTestCasesSPF as GTCSPF
from LLMs import GenerateTestCasesLLM as GTCLLM
import pandas as pd
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
        f4="gemini-2.0-flash"
        result=self.GTCLLM.driver_LLM(function,f4)
        print(result)



def main():
    functionsno=[12,13,15,22,23,24,27,32,34,35,36,37,38,39,40,41,42,43,44,45,53,54]
    functionsno=[i-1 for i in functionsno]
    functions=pd.read_csv("ExtractedData.csv",usecols=["Function"],skiprows= lambda x: x not in functionsno and x!=0) 
    functions=functions.drop_duplicates()
    print(functions)
    driver=Driver()
    for i in range(len(functions)):
        function=functions.iloc[i][0]
        driver.run_LLM(function)
        print("Function "+str(functionsno[i])+" completed")



main()