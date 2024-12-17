#!/bin/bash
set -x

# args #
while getopts ":c:m:" arg; do
  case "${arg}" in
  	c) mvnParams=$OPTARG;;
  	m) module=$OPTARG;;
    *) echo "Unknown Option $arg"
        usage
        exit 6;;
  esac
done

function usage {
  echo "$0: -c <mvn parameters> -m <module location>" >&2
}

# get dump #
mvn ${mvnParams} ${module} org.jacoco:jacoco-maven-plugin:0.7.8:dump \
  -Djacoco.address=localhost \
  -Djacoco.append=true
