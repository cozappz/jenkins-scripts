
//
import jenkins.*;
import jenkins.model.*;
import hudson.*;
import hudson.model.*;
import hudson.util.*;
import hudson.tasks.*;
import hudson.plugins.git.*;
import hudson.scm.*
import jenkins.scm.*

/*methods*/
def getDBGroupIDartifactID(String artifactID) {
  if (!"".equals(artifactID)) {
    return "eu.cec.regio.wave.database:wave-db-" + artifactID
  }
}

def getPomModulesGrpIdArtIdFromFile(FilePath workspacePath) {
  try{
    def file = new File(workspacePath.toString()+"\\pom.xml")
    def pomFile = new XmlSlurper().parse(file)
    def pomModulesGrpIdArtId = pomFile.modules.children().collect { getDBGroupIDartifactID(it.toString()) }.join(",")
    return pomModulesGrpIdArtId
  } catch (Exception ex){
    println ex.message
  }
}

println ""
println "binding=$binding"

try{
  def thread = Thread.currentThread().toString()
  println "thread=$thread"
}catch(Exception ex){
    
    println " ======================================================================================================================"
    println ex.message
    println " ======================================================================================================================"

}

  def job = this.binding.jenkinsProject
  println "job=$job"

  def workspace = job.workspace
  def scm = job.scm;
    
  if (scm instanceof hudson.scm.SubversionSCM) {

    println
    //scm.checkout(build, null/*Launcher not used*/, workspace /*workspace*/, listener/*listener*/, null /*changelogfile*/,null/*baseline*/)
    
  }else if (scm instanceof hudson.plugins.git.GitSCM) {
    
    scm.checkout(build, null/*Launcher not used*/, workspace /*workspace*/, listener/*listener*/, null /*changelogfile*/,null/*baseline*/)
    
  }
  return getPomModulesGrpIdArtIdFromFile(workspace)
