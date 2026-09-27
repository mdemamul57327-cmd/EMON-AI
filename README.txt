EMON AI LIVE MARKET ANALYZER

This project implements the requested flow:
- Floating EMON AI circle remains over other apps after overlay permission.
- User chooses screen capture permission once per Android session.
- When the bubble is tapped, the current screen is captured.
- The analyzer uses the CURRENT SCREEN, not a fixed market.
- A 5-second analysis window shows UP/DOWN + probability + explanation.
- A simple local visual heuristic examines green/red candle-like pixels.

IMPORTANT:
This is a prototype visual analyzer. It does not access Quotex's private API/feed and does not guarantee a correct next move.
A stronger production version should replace the heuristic with a trained chart model/OCR/market-data pipeline.

BUILD APK ON GITHUB
- Upload the project contents to a GitHub repository.
- The included .github/workflows/build-apk.yml builds app-debug.apk automatically.
- Open GitHub Actions, run "Build EMON AI APK", then download the artifact "EMON-AI-debug-apk".
