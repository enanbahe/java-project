pipeline {
  agent {
    label 'ant'
  }

  environment {
    MAJOR_VERSION = 1
  }

  stages {
    stage('Unit test') {
      steps {
        sh 'ant -f test.xml -v'
        junit 'reports/result.xml'
      }      
    }

    stage('Build') {
      steps {
        sh 'ant -f build.xml -v'
      }      
	  post {    
	    success {
	      archiveArtifacts artifacts: 'dist/*.jar', fingerprint: true   
	    }
	  }
    }

    stage('Artifact Repo') {
      steps {
        sh 'sudo aws s3 cp dist/rectangle_${env.MAJOR_VERSION}.${BUILD_NUMBER}.jar s3://eb-artifact-repo/java/branches/${BRANCH_NAME}/rectangle_${env.MAJOR_VERSION}.${BUILD_NUMBER}.jar'
      }      
    }

    stage('Test on Debian') {
      agent {
        docker {
          image 'openjdk:8u121-jre'
          reuseNode true
        }
      }
      steps {
        sh 'wget https://s3.amazonaws.com/eb-artifact-repo/java/branches/${BRANCH_NAME}/rectangle_${env.MAJOR_VERSION}.${BUILD_NUMBER}.jar'
        sh 'java -jar rectangle_${env.MAJOR_VERSION}.${BUILD_NUMBER}.jar 3 4'
      }
    }

    stage('Promote to master') {
      agent {
        label 'master'
      }
      when {
        branch 'development'
      }
      steps {
        echo 'Stashing any local changes...'
        sh 'git stash'
        echo 'Checking out development branch...'
        sh 'git checkout development'
        echo 'Pulling to origin...'
        sh 'git pull origin'
        echo 'Checking out master branch...'
        sh 'git checkout master'
        echo 'Merging development into master...'
        sh 'git merge development'
        echo 'Pushing to origin master...'
        sh 'git push origin master'
        echo 'Tagging a release...'
        sh 'git tag rectangle-${env.MAJOR_VERSION}.${BUILD_NUMBER}'
        sh 'git push origin rectangle-${env.MAJOR_VERSION}.${BUILD_NUMBER}'
      }
    }

    stage('Release') {
      when {
        branch 'master'
      }
      steps {
        sh 'sudo aws s3 cp dist/rectangle_${env.MAJOR_VERSION}.${BUILD_NUMBER}.jar s3://eb-artifact-repo/java/releases/rectangle_${env.MAJOR_VERSION}.${BUILD_NUMBER}.jar'
      }      
    }
  }
}
