Catalog [![Build Status](https://travis-ci.org/PDOK/catalog-java.svg?branch=master)](https://travis-ci.org/PDOK/catalog-java) [![Maven Central](https://img.shields.io/maven-central/v/nl.pdok/pdok-catalog-java.svg?maxAge=2592000)]()
=======

Java (file system) catalog interface.

Catalog-java accesses the file system catalogus which is stored in Github and on the local hard-drive.

The Catalogus Git-branch is downloaded and unpacked using the "GitInteractionsHandler" class.

JobEntries are retrieved using the JobEntriesReader class.

## Release

For releasing the Catalog-java execute the command:

```jshelllanguage
./prepare_release.sh
```

The command asks 2 questions.
1. Release version?
    - This is the version in the pom.xml (**without -SNAPSHOT**)
2. New development version?
    - This is the version in the pom.xml. (**with -SNAPSHOT**)

When the command is finished push the changes to github. **Don't forget to push the tags, or there is no release**

If done go to [Travis](https://travis-ci.org/PDOK/catalog-java). Login if needed.
In the project pdok-catalog-java click on branches. Check if the branches master and pdok-catalog-java-{YOUR_VERSION} is build.
Click on the branch pdok-catalog-java-{YOUR_VERSION} check if the job 'JDK:oraclejdk7' is done.

Visit [Sonartype](https://oss.sonatype.org/#nexus-search;quick~pdok) to check if your version of pdok-catalog-java is present.
Upon release, your component will be published to Central: this typically occurs within 10 minutes, though updates to search can take up to two hours

More information about [Sonartype](http://central.sonatype.org/pages/ossrh-guide.html)
