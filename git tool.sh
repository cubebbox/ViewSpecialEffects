#!/bin/bash

rname="stpro"
rbrance="st-pro"
lbranch="st-pro"
rurl="https://github.com/cubebbox/ViewSpecialEffects.git"
name="cubebox"
email="luozisong@dz.tt"

cfont(){
while (($#!=0))
do
        case $1 in
                -b)
                        echo -ne " ";
                ;;
                -t)
                        echo -ne "\t";
                ;;
                -n)     echo -ne "\n";
                ;;
                -black)
                        echo -ne "\033[30m";
                ;;
                -red)
                        echo -ne "\033[31m";
                ;;
                -green)
                        echo -ne "\033[32m";
                ;;
                -yellow)
                        echo -ne "\033[33m";
                ;;
                -blue)
                        echo -ne "\033[34m";
                ;;
                -purple)
                        echo -ne "\033[35m";
                ;;
                -cyan)
                        echo -ne "\033[36m";
                ;;
                -white|-gray) echo -ne "\033[37m";
                ;;
                -reset)
                        echo -ne "\033[0m";
                ;;
                -h|-help|--help)
                        echo "Usage: cfont -color1 message1 -color2 message2 ...";
                        echo "eg:       cfont -red [ -blue message1 message2 -red ]";
                ;;
                *)
                echo -ne "$1"
                ;;
        esac
        shift
done
}

anykeyback(){
	cfont -green "操作结束,按任意键返回菜单"
	>/dev/null read -n 1
}

clear

cfont -cyan " 
================================================================================
                          .8@@@A:                  
                         G@@@@@A                  
                        .#@@@@@@;      rGGXs      
                         iXB#M&1       A@@@S      
                                     ,8@@@@5               
     ,rh5S3S51sssssss; .SSSSSSSS. r8&#@@@@@BXXXXX;
   5A@@@@@@@@@@@@@@@@& 1@@@@@@@@; X@@@@@@@@@@@@@@s
 .H@@@@@&9S3X#@@@@&55r ,sS#@@@@@: ;ssX@@@@@Gsssss.
 9@@@@#r     :M@@@M,      B@@@@@:    3@@@@@5      
 &@@@@M       B@@@@S      B@@@@@:    9@@@@@S      
 1@@@@@&s;:;1&@@@@@i      B@@@@@:    9@@@@@S      
  hH@@@@@@@@@@@@@Bs       B@@@@@:    9@@@@@5      
   rM@@#HBMMMH&9s         B@@@@@:    9@@@@@3      
 :X@@@#h:;;:,.           .B@@@@@;    5@@@@@#855S81
.B@@@@@@@@###MBHAGSi   5M#@@@@@@@MG  .&@@@@@@@@@@#
 1M@@@@@@@@@@@@@@@@@X. 5HHAAAAAAAH8    r3XAHA&G9hi
 rB@@M9988GXX&H@@@@@@1                            
8@@@@5         &@@@@@r                            
3@@@@#&9SSSS38H@@@@@3                             
 :8HM@@@@@@@@@@#BX5,    

================================================================================"      

sleep 1
clear


showMenu(){
cfont -cyan "
================================================================================
当前库位置：$PWD
当前远程库：$rname  当前远程分支：$rbrance 当前本地分支：$lbranch
请选择选择编号指定您的操作:										   	  				
0 退出

1 初始化项目			2 拉取(pull)		3 从远程获取（fetch） 	
4 提交到本地			5 提交到服务器		6 一键提交
7 一键修复（无法提交或更新）	8 切换分支		9 查看当前分支 												 
================================================================================
"
}

showMenu

dealChose(){
read -p "" chose

if [[ $chose == "0" ]]; then
	exit
elif [[ $chose == "1"   ]]; then

	git init
	git config --global credential.helper store
	git config --global user.name $name
	git config --global user.email $email
	git remote add $rname $rurl
	git checkout -b $lbranch

	anykeyback
	clear
	showMenu
	dealChose
elif [[ $chose == "2" ]]; then
	cfont -green "拉取数据中。。。\n"
	git pull $rname $rbrance
	anykeyback
	clear
	showMenu
	dealChose
elif [[ $chose == "3"  ]]; then
	cfont -green "获取数据中。。。\n"
	git fetch $rname
	cfont -green "是否进行merge合并到本地分支（y/n）\n"
	read -p "" o
	if [[ $o == "y" ]]; then
		git merge $rname/$lbranch	
	fi

	anykeyback
	clear
	showMenu
	dealChose
elif [[  $chose == "4"  ]]; then
	cfont -green "请输入提交内容:"
	read -p "" c
	cfont -green "添加中。。。\n"
	git add .
	git commit -a -m $c

	anykeyback
	clear
	showMenu
	dealChose
elif [[ $chose == "5" ]]; then
	git push $rname $lbranch
	anykeyback
	clear
	showMenu
	dealChose
elif [[ $chose == "6" ]]; then
	cfont -green "请输入提交内容:"
	read -p "" c
	cfont -green "一键提交中。。。\n"
	if [[ $c == "" ]]; then
		c="auto"
	fi
	git add .
	git commit -a -m $c
	git push $rname $lbranch

	anykeyback
	clear
	showMenu
	dealChose
elif [[ $chose == "7" ]]; then
	cfont -red "此操作可能会丢失掉别人的已提交数据，是否确认执行？(y/n)\n"
	read -p "" o
	if [[ $o == "y" ]]; then
		cfont -green "提交中。。。\n"
		git add .
		git commit -a -m "自动强制提交"
		git push $rname $lbranch -f
	else
		cfont -green "已取消操作\n"
	fi
	
	anykeyback
	clear
	showMenu
	dealChose	
elif [[ $chose == "8" ]]; then
	echo "8"
	anykeyback
	clear
	showMenu
	dealChose
elif [[ $chose == "9" ]]; then
	echo "9"
	anykeyback
	clear
	showMenu
	dealChose
else
	anykeyback
	clear
	showMenu
	dealChose
fi

}


dealChose




