import pandas as pd
import matplotlib.pyplot as plt

# def Bar_Chart():
#     # Load DataFrame
#     df = pd.read_csv("FilteredDataWithoutDuplicates.csv")  # Update with actual file

#     # Create three groups
#     low_complexity = df[df['cyclomatic_complexity'] < 3].shape[0]      # Complexity < 5
#     medium_complexity = df[(df['cyclomatic_complexity'] >= 3) & (df['cyclomatic_complexity'] <= 10)].shape[0]  # 5 <= Complexity <= 10
#     high_complexity = df[df['cyclomatic_complexity'] > 10].shape[0]    # Complexity > 10

#     # Data for plotting
#     categories = ["Complexity < 3", "3 ≤ Complexity ≤ 10", "Complexity > 10"]
#     values = [low_complexity, medium_complexity, high_complexity]

#     # Plot Bar Chart
#     plt.figure(figsize=(8, 5))
#     plt.bar(categories, values, color=['green', 'orange', 'red'])

#     # Labels & Title
#     plt.xlabel("Cyclomatic Complexity Groups")
#     plt.ylabel("Number of Functions")
#     plt.title("Cyclomatic Complexity Grouped Analysis")

#     # Display Data Labels on Bars
#     for i, v in enumerate(values):
#         plt.text(i, v + 0.5, str(v), ha='center', fontsize=12)

#     # Show the graph
#     plt.show()

# # Run function
# Bar_Chart()



# import pandas as pd

# def Show_Complexity_Groups():
#     # Load DataFrame
#     df = pd.read_csv("FilteredDataWithoutDuplicates.csv")  # Update with actual file

#     # Count occurrences of each unique complexity value
#     complexity_counts = df['cyclomatic_complexity'].value_counts().sort_index()

#     # Print column headers
#     print(f"{'Cyclomatic Complexity':<25}{'Number of Functions'}")
#     print("=" * 45)

#     # Print grouped data with aligned formatting
#     for complexity, count in complexity_counts.items():
#         print(f"{complexity:<25}{count}")

# # Run function
# Show_Complexity_Groups()






df=pd.read_csv("FilteredData_Test_Updated_output2.csv")
df=df[df["cyclomatic_complexity"]<=10]
df.to_csv("FilteredData_Test_Updated_output3.csv", index=False)