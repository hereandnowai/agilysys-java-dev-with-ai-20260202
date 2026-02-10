#!/usr/bin/env python3
import os
import argparse
import tiktoken

TEXT_EXTS = {
    ".py",".js",".ts",".jsx",".tsx",".md",".txt",
    ".json",".yaml",".yml",".html",".css",".scss",".java",
    ".c",".cpp",".h",".hpp",".rs"
}

MODEL_ENCODING_OVERRIDES = {
    "gpt-oss:20b": "cl100k_base",
}

def is_text_file(path):
    _, ext = os.path.splitext(path.lower())
    return ext in TEXT_EXTS

def count_file_tokens(path, encoder):
    try:
        with open(path, "r", encoding="utf-8") as f:
            txt = f.read()
    except Exception:
        return 0
    return len(encoder.encode(txt))


def get_encoder(model):
    try:
        return tiktoken.encoding_for_model(model)
    except KeyError:
        encoding_name = MODEL_ENCODING_OVERRIDES.get(model)
        if not encoding_name:
            raise
        print(
            f"Model {model} has no preset encoder; falling back to '{encoding_name}'."
        )
        return tiktoken.get_encoding(encoding_name)

def main(root=".", model="gpt-4o", include_hidden=False):
    encoder = get_encoder(model)
    total = 0
    details = []
    for dirpath, dirnames, filenames in os.walk(root):
        if not include_hidden:
            # skip hidden dirs
            dirnames[:] = [d for d in dirnames if not d.startswith(".")]
        for fn in filenames:
            if not include_hidden and fn.startswith("."):
                continue
            fp = os.path.join(dirpath, fn)
            if not is_text_file(fp):
                continue
            tokens = count_file_tokens(fp, encoder)
            if tokens:
                details.append((tokens, fp))
                total += tokens
    details.sort(reverse=True)
    for tok, path in details:
        print(f"{tok:8d}  {path}")
    print("="*40)
    print(f"Total tokens ({model}): {total}")

if __name__ == "__main__":
    p = argparse.ArgumentParser()
    p.add_argument("path", nargs="?", default=".")
    p.add_argument("--model", default="gpt-4o",
                   help="Model name (used to select encoding).")
    p.add_argument("--include-hidden", action="store_true",
                   help="Include dotfiles and dotdirs.")
    args = p.parse_args()
    main(args.path, args.model, args.include_hidden)