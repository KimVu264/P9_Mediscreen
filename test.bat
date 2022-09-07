
cd patient
call mvn surefire-report:report
cd ..\note
call mvn surefire-report:report
cd ..\assessment
call mvn surefire-report:report
