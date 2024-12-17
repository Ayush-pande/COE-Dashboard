#!/bin/sh -x
sleep 10
#apply all mocks to mock server
for file in ./pacts/*.json
do
   curl -H'Content-Type: application/json' -d@$file http://localhost:8080/
done
#prevent docker container restart
mkfifo ready 2>/dev/null
read < ready

