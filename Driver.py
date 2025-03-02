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
        valid,failures=self.jqf.driverjqf(function_code)
        model="gemini-1.5-flash"
        result=self.GTCJQF.gen_TC_Gemini(valid,failures,function_code,model)
        return valid,failures,result

    def run_jqf_with_compile(self,df):
        self.jqf.make_files(df)
        model="gemini-1.5-flash"
        i=True
        df[['valid_jqf', 'failure_jqf', 'JUnit_jqf']] = None
        for index, row in df.iterrows():
            function_code = row['Function']
            valid,failures=self.jqf.driver_all(function_code,i)
            i=False
            result=self.GTCJQF.gen_TC_Gemini(valid,failures,function_code,model)
            df.at[index, 'valid_jqf'] = valid
            df.at[index, 'failure_jqf'] = failures
            df.at[index, 'JUnit_jqf'] = result
            print("Index is",index)
        df.to_csv("FilteredData_Test_Updated.csv", index=False)

            

    def run_spf(self,function):
        result=self.spf.driverspf(function)
        f4="gemini-1.5-flash"
        result=self.GTCSPF.gen_TC_test(function,result,f4)
        print(result)
    
    def run_LLM(self,function):
        f4="gemini-2.0-flash"
        result=self.GTCLLM.driver_LLM(function,f4)
        print(result)



def main():
    driver=Driver()
    df=pd.read_csv("FilteredData_Test.csv")
    df=df.drop(index=23)
    df=df.drop(index=22)
    df=df.drop(index=25)
    # print(df['Function'])
    df = df.reset_index(drop=True)
    driver.run_jqf_with_compile(df)
main()