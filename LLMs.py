from langchain_google_genai import GoogleGenerativeAI as GGAI
from langchain_openai import ChatOpenAI as COAI
import os
from dotenv import load_dotenv



class GenerateTestCasesLLM:
    def gen_TC_Gemini(self,func,model):
        try:
            llm=GGAI(model=model,api_key=os.getenv("Google_API_KEY"))
            messages=[
                ("system","You are going to generate Junit4 file  for the given Java function and do not write anything else like ```java or ```"),
                ("human",func)
            ]
            response=llm.invoke(messages)
            return response
        except Exception as e:
            print(e)
            return e
        
    def gen_TC_GPT(self,func,model):
        try:
            llm=COAI(model=model,api_key=os.getenv("OPENAI_API_KEY"))
            messages=[
                ("system","You are going to generate Junit4 file  for the given Java function and do not write anything else like ```java or ```"),
                ("human",func)
            ]
            response=llm.invoke(messages)
            return response.content
        except Exception as e:
            print(e)
            return e



def main():
    load_dotenv()
    obj=GenerateTestCasesLLM()
    func="""public class Factorial {

    // Function to calculate factorial
    public static int factorial(int n) {
        if (n == 0) {
            return 0;
        }
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}"""
    result=obj.gen_TC_GPT(func,"gpt-4o-mini")

    if isinstance(result,Exception):
        print(result)
        return
    with open("GPTTestCases.java","w") as f:
        f.write(result)
    print(result)

main()
