@echo off
rem /**
rem  *
rem  * Author: jindong.lin@baozun.com
rem  */
echo.
echo [��Ϣ] ��������·����
echo.
pause
echo.

cd %~dp0
cd..

call mvn clean

cd bin
pause