from openai import OpenAI

# Initialize the client
client = OpenAI(api_key="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")

# Test API call
completion = client.chat.completions.create(
    model="gpt-4o-mini",
    messages=[
        {
            "role": "user",
            "content": "Hello, how are you?"
        }
    ]
)

# Print the response
print(completion.choices[0].message.content)
