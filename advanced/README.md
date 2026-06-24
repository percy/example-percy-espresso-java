# Advanced Percy + Espresso (Java)

This directory documents the advanced Percy SDK feature surface for `io.percy:espresso-java`. See the basic example at `app/src/androidTest/.../EnsureInputTests.java` for the minimum integration.

## Layout asymmetry

Espresso tests must live under `app/src/androidTest/` per the Android instrumentation source-set convention — they can't be relocated under `advanced/` without breaking the gradle build. As a result, the advanced test code lives at:

`app/src/androidTest/java/com/sample/browserstack/samplecalculator/AdvancedScreenshotTests.java`

…while this `advanced/` directory holds only the `matrix.yml` (canonical row mapping) and this README.

## What the advanced test covers

5 `@Test` methods in `AdvancedScreenshotTests.java`, one per applicable matrix row:

- `status_bar_height` (via `ScreenshotOptions.setStatusBarHeight`)
- `nav_bar_height` (via `ScreenshotOptions.setNavigationBarHeight`)
- `fullscreen` (via `ScreenshotOptions.setFullscreen`)
- Build metadata via env (`PERCY_PROJECT` / `PERCY_BUILD` / `PERCY_BRANCH` / `PERCY_COMMIT` — read by the SDK at upload time)
- Baseline screenshot without options

Other native matrix rows (orientation, ignore/consider regions, sync, test_case, labels) are marked `Planned` in `matrix.yml` — `io.percy:espresso-java` 1.0.3 exposes a narrower `ScreenshotOptions` surface than the Appium SDK family.

Web-only rows (widths, percyCSS, etc.) are marked `N/A`.

## Run locally

Requires an Android emulator or connected device + Percy CLI + `PERCY_TOKEN`:

```bash
export PERCY_TOKEN="<your project token>"
npx @percy/cli@^1.31.13 app:exec -- ./gradlew connectedDebugAndroidTest
```

## CI note

The advanced CI job is `workflow_dispatch`-only. Running Espresso in GitHub Actions requires an Android emulator runner (`reactivecircus/android-emulator-runner`), which is heavy. See `.github/workflows/advanced.yml`.

## Coverage matrix

States: `Covered` / `N/A — <reason>` / `Planned` / `Deprecated`. Source of truth is [`matrix.yml`](./matrix.yml).
