node {
    try {
        def buildVersion
        def isRelease
        def isDemo
        stage('Setup') {
            prepareEnv()
            checkout scm
            sh "git reset --hard HEAD"
            sh "chmod u+x gradlew"
        }
        stage('Build') {
            if (isRelease) {
                sh "./gradlew -Prelease --info --stacktrace clean buildPlugin"
            } else {
                sh "./gradlew --info --stacktrace clean buildPlugin"
            }
        }
    } catch (any) {
        any.printStackTrace()
        currentBuild.result = 'FAILURE'
        throw any
    } finally {
        junit allowEmptyResults: true, testResults: 'build/test-results/**/*.xml'
        archiveArtifacts artifacts: 'build/reports/**', allowEmptyArchive: true
    }
}

def prepareEnv() {
    def jdkHome = tool name: 'JDK8', type: 'jdk'
    env.PATH = "${jdkHome}/bin:${env.PATH}"
    env.JAVA_HOME = "${jdkHome}"
    env.GRADLE_USER_HOME = "${WORKSPACE}/gradle-home"
    sh """
		mkdir -p ${env.GRADLE_USER_HOME}
		echo 'org.gradle.java.home=${env.JAVA_HOME}' > ${env.GRADLE_USER_HOME}/gradle.properties
	"""
    jdkHome = null
}