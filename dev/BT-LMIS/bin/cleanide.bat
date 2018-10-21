@echo off
rem /**
rem  * Author: jindong.lin@baozun.com
rem  */
echo.
echo [信息] 清理Eclipse工程文件。
echo.
pause
echo.

cd %~dp0

cd..
call mvn -Declipse.workspace=%cd% eclipse:clean eclipse:eclipse

del .classpath
del .project

pause