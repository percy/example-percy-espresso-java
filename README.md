# example-percy-espresso-java
Example app used by the [Percy Espresso Java tutorial](https://docs.percy.io/v2-app/docs/espresso) demonstrating Percy's Espresso integration.

## Percy Espresso Java Tutorial

The tutorial assumes you're already familiar with Espresso and Java and focuses on using it with App Percy. You'll still
be able to follow along if you're not familiar with Espresso & Java, but we won't
spend time introducing Espresso & Java concepts.

The tutorial also assumes you have [git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git) installed.

### Step 1

Clone the example application and install dependencies:

```bash
$ git clone https://github.com/percy/example-percy-espresso-java
$ cd example-percy-espresso-java
```

### Step 2
# Build ipa & testSuite

We have provided you with a sample application and testSuite in the resources folder that you can use to get started without following the rest of step 2. If you want to use these, simply skip to step 3.

Alternatively, you can need to build your ipa & testSuite from the sample project.

1. Build the main application: `./gradlew assemble` (apk will be generated in the app/build/outputs/apk/debug/ directory)
2. Build the test application: `./gradlew assembleAndroidTest` (apk will be generated in the app/build/outputs/apk/androidTest/debug/ directory)

### Step 3

In case you are choosing to use Browserstack App Automate, please follow following steps:

1. You will need a BrowserStack `username` and `access key`. To obtain your access credentials, [sign up](https://www.browserstack.com/users/sign_up?utm_campaign=Search-Brand-India&utm_source=google&utm_medium=cpc&utm_content=609922405128&utm_term=browserstack) for a free trial or [purchase a plan](https://www.browserstack.com/pricing).

2. Please get your `username` and `access key` from [profile](https://www.browserstack.com/accounts/profile) page.

You can upload app & testSuite using following curl commands
```bash
$ curl -u "<username>:<access key>" \
    -X POST "https://api-cloud.browserstack.com/app-automate/espresso/v2/app" \
    -F "file=@/path/to/app/file/app-debug.apk"
```

```bash
$ curl -u "<username>:<access key>" \
  -X POST "https://api-cloud.browserstack.com/app-automate/espresso/v2/test-suite" \
  -F "file=@/path/to/app/file/app-debug-test.apk"
```

You can also follow [`upload your app`](https://www.browserstack.com/docs/app-automate/espresso/getting-started#2-upload-your-app) if using App Automate for latest instructions.

### Step 4

Sign in to Percy and create a new `app` type project. You can name the project "test-project" if you'd like. After you've created the project, you'll be shown a token environment variable.

### Step 5

Execute tests using following command post updating appPercy params

```bash
$ curl -u "<username>:<access key>" \
  -X POST "https://api-cloud.browserstack.com/app-automate/espresso/v2/build" \
  -d '{"devices": ["Samsung Galaxy S9 Plus-9.0"],
    "appPercy": {"env": {"PERCY_BRANCH": "test"}, "PERCY_TOKEN": "<PERCY_TOKEN>"},
    "app": "bs://<APP_ID>", "testSuite": "bs://<TEST_SUITE_ID>"}' \
  -H "Content-Type: application/json"
```

This will run the app's Espresso tests, which contain calls to create Percy screenshots. The screenshots
will then be uploaded to Percy for comparison. Percy will use the Percy token you used in **Step 2**
to know which organization and project to upload the screenshots to.

You can view the screenshots in Percy now if you want, but there will be no visual comparisons
yet. You'll see that Percy shows you that these screenshots come from your `<PERCY_BRANCH>` branch.

### Step 5

Update `EnsureInputTests.java` with following changes

- Update Resource id in ensureSingleInputIsHandled test to `R.id.buttonTwo`.

or

Use testSuite_2 from resources folder

Follow Step 2 & 3 to generate updated testSuite and upload it to App Automate, only testSuite update is required unless changes are made to main app.

### Step 7

Run the tests with screenshots again similar to Step 5

This will run the tests again and take new screenshots of our modified application. The new screenshots
will be uploaded to Percy and compared with the previous screenshots, showing any visual diffs.

### Step 8

Visit your project in Percy and you'll see a new build with the visual comparisons between the two
runs. Click anywhere on the Build 2 row. You can see the original screenshots on the left, and the new
screenshots on the right.

Percy has highlighted what's changed visually in the app! Snapshots with the largest changes are
shown first You can click on the highlight to reveal the underlying screenshot.

If you scroll down, you'll see that no other test cases were impacted by our changes to text.
The unchanged screenshots are shown under `Unchanged` filter and are hidden by default.

### Finished! 😀

From here, you can try making your own changes to the app and tests, if you like. If you do, re-run
the tests and you'll see any visual changes reflected in Percy.

### Espresso Build Params

For App Automate session to work with App percy we need to provide appPercy params. For instance, in `"appPercy": {"env": {"PERCY_BRANCH": "test"}, "PERCY_TOKEN": "<PERCY_TOKEN>"}`, the env parameter can contain any of following Percy [enviornment variables](https://docs.percy.io/v2-app/docs/environment-variables).

App Percy supports sharding with App Automate, however, it is recommended to use `all` deviceSelection strategy when using App Percy. Using `any` deviceSelection strategy can produce inconsistent builds as a random device from list of devices provided will be mapped to shards and you build will end up seeing new snapshots.

### Running in local Android Studio

1. Add below code in your test file.
  ```java
    @Before
    public void setUp() {
        CliWrapper.PERCY_SERVER_ADDRESS = "http://<IP>:5338";
        appPercy = new AppPercy();
    }
  ```
  - For simulators you can also use `10.0.2.2` as IP
  - For real devices get IP of your mac/win using `ifconfig` in mac and `ipconfig` in windows.
2. export PERCY_TOKEN=<PERCY_TOKEN>
3. execute `percy app:exec start` to start percy server.
4. Run Espresso tests
5. execute `percy app:exec stop` to stop percy server.