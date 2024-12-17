#!/usr/bin/env groovy



node("master") {
    checkout scm
    library identifier: "single_ci@1.12.0",
            retriever: modernSCM([
                    $class: 'GitSCMSource',
                    credentialsId: 'ms360-ci-private_key',
                    id: 'ms360-ci-private_key',
                    remote: 'ssh://git@bitbucket:7999/jco/single-ms-ci-shared-lib.git',
                    traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]
            ])
    ms360_ci_pipeline ms_config_file_path:".ms360-ci/pipeline-config.yaml",
    log_level:"INFO"
}