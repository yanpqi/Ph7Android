#!/bin/bash

# Should config your project vars here.
source $(cd "$(dirname "${BASH_SOURCE[0]}")";pwd)/.bootstrap.sh
source /opt/utils/alias.sh

# Function area.
function remove_apk {
    adb uninstall $PRODUCT_PKG
}

function install_apk {
    target_product="$PRODUCT_LOCATION/$PRODUCT_FILE_NAME"
    remove_apk
    adb install $target_product
    if [ $? -eq 0 ]; then
        desktop_notify "$PRODUCT_PKG install sucessfully!" "true"
    fi
    echo $PRODUCT_LUNCHER
    adb shell am start $PRODUCT_LUNCHER
}

function new_apk {
    bash "$TOP_PATH/gradlew" aDebug
    if [ $? -ne 0 ]; then
        desktop_notify "$PRODUCT_PKG compile failed" ""
        return -1
    fi
    install_apk
}

function clear_apk {
   bash "$TOP_PATH/gradlew" clean
}

function android_log {
   if [ "$1" == '' ]; then
       adb logcat -c
    else:
       adb logcat | grep "$1"
    fi
}

# Commands shortcut.
alias bl.n='new_apk'
alias bl.i='install_apk'
alias bl.c='clear_apk'
alias bl.r='remove_apk'

alias dv.lg='android_log'

alias 2s='cd $TOP_PATH/app/src/main/java/com/tribodys/ph7_android'
alias 2='cd $TOP_PATH'
