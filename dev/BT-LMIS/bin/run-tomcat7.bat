@echo off
rem /**
rem  * Author: jindong.lin@baozun.com
rem  */
title %cd%
echo.
echo [��Ϣ] ʹ��Tomcat7������й��̡�
echo.
rem pause
rem echo.

cd %~dp0
cd..

set MAVEN_OPTS=%MAVEN_OPTS% -Xms256M -Xmx512M -XX:PermSize=256m -XX:MaxPermSize=512m -Xss2048K
call mvn tomcat7:run

cd bin
pause