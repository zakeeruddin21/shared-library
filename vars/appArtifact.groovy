def getNewArtifactVersion(build, majorVersion, minorVersion) {
  int patch=getLastArtifactPatchVersion(build.getPreviousBuild());
  patch+=1;
  String version = majorVersion+"."+minorVersion+"."+patch.toString();
  build.displayName = "${env.BUILD_NUMBER} #${version}"
  // setBuildNameAndDescription(version, "SCM Checkout");
  return version;
}

def showEnvVar() {
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
