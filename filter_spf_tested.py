import pandas as pd


filtered_data=pd.read_csv("FilteredData.csv")
filtered_data = filtered_data.dropna(subset=["ExtractedInput"])

filtered_data = filtered_data.reset_index(drop=True)

filtered_data.to_csv("FilteredData_Test.csv",index=False)
