String artifactPickerScript = getPickerScriptFor();
	
def getPickerScriptFor() {
	return '''import jenkins.model.Jenkins;
	import hudson.model.AbstractProject;
	import hudson.model.Result;
	import hudson.util.RunList;
	import hudson.model.TaskListener;

	def buildJob = Jenkins.getInstance().getItem(\'PipelineOne\');
	RunList<?> builds = buildJob.getBuilds().overThresholdOnly(Result.SUCCESS);

	List<String> artifactVersions = [];
	if(ENV.toString() != \'dev\') {
	  String previousEnv = \'\'
	  if(ENV.toString() == \'qa\') {
	    previousEnv = \'dev\'
	  }
	  else if(ENV.toString() == \'prf\') {
	    previousEnv = \'qa\'
	  }

	  def list = builds.limit(20).collect { build ->
	      if(build.getHasArtifacts() && build.getEnvironment(TaskListener.NULL)["ENV"] == previousEnv) {
		build.getArtifactsDir().toString() + "/" + build.getArtifacts().toString().replaceAll("\\\\[", "").replaceAll("\\\\]", "");
	      }
	  };

	  list.each { file ->
	    if(file != null) { 
	      artifactVersions.add(new File(file.toString()).text);
	    }
	  }
	}

	return (artifactVersions.size()==0 ? [\'NA\'] : artifactVersions);''';
}

def getNewArtifactVersion(build, majorVersion, minorVersion) {
  int patch=getLastArtifactPatchVersion(build.getPreviousBuild());
  patch+=1;
  String version = majorVersion+"."+minorVersion+"."+patch.toString();
  build.displayName = "${env.BUILD_NUMBER} #${version}"
  // setBuildNameAndDescription(version, "SCM Checkout");
  return version;
}

def showEnvVar() {
  println "In showEnvVar()"
  showEnvVar("testing overload")
  // println currentBuild.number
}

def showEnvVar(str) {
  println "In showEnvVar(str) - ${str}"
  println currentBuild.number
}


def int getLastArtifactPatchVersion(build) {
	if (build == null) {
		return 0;
	}

	if(build != null
			&& build.buildVariables.appEnvironment.toString().equals("dev")
			&& (build.buildVariables.containsKey("artifactVersion")
				&& !build.buildVariables.artifactVersion.toString().equals("")))
	{
		return build.buildVariables.artifactVersion.toString().split("\\.")[2].toInteger();
	}
	//Recurse now to handle in chronological order
	getLastArtifactPatchVersion(build.getPreviousBuild());
}
