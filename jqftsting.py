
with open("JQF-wsl/java-fuzzing-example/fuzz-results/corpus/id_000000", "rb") as f:
    binaryf=f.read().hex()


data = bytes.fromhex(binaryf)
print(list(data))
