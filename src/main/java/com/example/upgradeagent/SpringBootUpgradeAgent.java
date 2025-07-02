package com.example.upgradeagent;

import service.GPTService;

public class SpringBootUpgradeAgent {

  private static final String PROJECT_PATH = "/Users/Vishnu/REPOS/AIDEMO/ecommerce-auditlog";
  private static final String OPENAI_API_KEY = "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";

  public static void main(String[] args) throws Exception {
      String files = ProjectScanner.loadSpringFiles(PROJECT_PATH);
      String prompt = PromptBuilder.buildUpgradePrompt(files);

      //String suggestion = GPTService.getSpringBootVersionUpgradeSuggestions(OPENAI_API_KEY);
      //System.out.println("🧠 GPT Suggestions:\n" + suggestion);

   String latestVersion = GPTService.getLatestSpringBootVersion(OPENAI_API_KEY);

      // Optional: Auto-edit pom.xml to upgrade Spring Boot version
    PomModifier.updateSpringBootVersion(PROJECT_PATH, latestVersion);
    String latestOpenReWriteVersion = GPTService.getLatestOpenRewriteRecipe(OPENAI_API_KEY,latestVersion);
      // Run OpenRewrite recipes (via shell)
    PomModifier.addRewritePlugin(PROJECT_PATH,latestOpenReWriteVersion);
    //PomModifier.addDependencyRewrite(PROJECT_PATH);
    //String latestOpenReWriteVersion = "org.openrewrite.java.spring.boot3.UpgradeSpringBoot_3_0";
    RewriteRunner.runRewrite(PROJECT_PATH,latestOpenReWriteVersion);

      // Optional: Git commit
      //GitCommitHelper.commitChanges(PROJECT_PATH, "Upgrade Spring Boot via AI agent");
    }

  // Optional: apply changes using PomUpdater here
    }
