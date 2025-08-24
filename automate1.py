
import hashlib
import pandas as pd

def _norm_func_key(func_text: str) -> str:
    if not isinstance(func_text, str):
        return ""
    cleaned = " ".join(func_text.split())
    return hashlib.sha1(cleaned.encode("utf-8")).hexdigest()

def merge_result_csvs(
        base_csv: str = "detailed_combined_results.csv",
        jqf_csv: str = "a_jqf_process_res.csv",
        spf_csv: str = "a_spf_process_res.csv",
        llm_csv: str = "a_llm_process_res.csv",
        out_csv: str = "merged_results.csv"
    ) -> pd.DataFrame | None:
    try:
        base_df = pd.read_csv(base_csv)
    except Exception as e:
        print(f"Base load failed: {e}")
        return None
    if "Function" not in base_df.columns:
        print("Base file missing 'Function' column.")
        return None

    base_df["__FUNC_KEY__"] = base_df["Function"].apply(_norm_func_key)
    base_df = base_df.drop_duplicates("__FUNC_KEY__")

    def load_optional(path, tag):
        try:
            df = pd.read_csv(path)
            if "Function" not in df.columns:
                print(f"{tag} missing Function column, skipped.")
                return None
            df["__FUNC_KEY__"] = df["Function"].apply(_norm_func_key)

            # Renames / passthrough handling
            rename_map = {}
            # Generic single LLM result (older files)
            if "LLM_Result" in df.columns:
                rename_map["LLM_Result"] = f"LLM_Result_{tag}"
            # New dual LLM results
            if "LLM_Result_Statement" in df.columns:
                rename_map["LLM_Result_Statement"] = "LLM_Result_Statement"
            if "LLM_Result_Branch" in df.columns:
                rename_map["LLM_Result_Branch"] = "LLM_Result_Branch"
            # JQF / SPF values
            if "JQF_Test_Values" in df.columns:
                rename_map["JQF_Test_Values"] = "JQF_Test_Values"
            if "SPF_Test_Values" in df.columns:
                rename_map["SPF_Test_Values"] = "SPF_Test_Values"

            if rename_map:
                df = df.rename(columns=rename_map)

            # Drop columns we don't want duplicated over base
            drop_cols = [c for c in ["Class_Name", "Function"] if c in df.columns]
            if drop_cols:
                df = df.drop(columns=drop_cols)

            # Keep only key + new data columns
            keep_cols = ["__FUNC_KEY__"] + [c for c in df.columns if c != "__FUNC_KEY__"]
            df = df[keep_cols].drop_duplicates("__FUNC_KEY__")
            # Drop empty (only key) frames
            if len(df.columns) == 1:
                return None
            return df
        except FileNotFoundError:
            print(f"{tag} file not found; skipping.")
            return None
        except Exception as e:
            print(f"{tag} load error: {e}")
            return None

    jqf_df = load_optional(jqf_csv, "JQF")
    spf_df = load_optional(spf_csv, "SPF")
    llm_df = load_optional(llm_csv, "LLM")

    merged = base_df.copy()

    for addon, tag in [(jqf_df, "JQF"), (spf_df, "SPF"), (llm_df, "LLM")]:
        if addon is not None:
            print(f"Merging {tag}...")
            merged = merged.merge(addon, on="__FUNC_KEY__", how="left")

    # Preferred ordering (includes dual LLM results)
    prefer_order = [c for c in [
        "Class_Name",
        "Function",
        "JQF_Test_Values",
        "SPF_Test_Values",
        "LLM_Result_JQF",
        "LLM_Result_SPF",
        "LLM_Result_LLM",
        "LLM_Result_Statement",
        "LLM_Result_Branch"
    ] if c in merged.columns]

    other_cols = [c for c in merged.columns if c not in prefer_order and c != "__FUNC_KEY__"]
    merged = merged[prefer_order + other_cols]

    merged.to_csv(out_csv, index=False)
    print(f"Merged saved to {out_csv} (rows={len(merged)})")
    return merged

if __name__ == "__main__":
    merge_result_csvs()
