#!/bin/bash
rm -f /tmp/.X99-lock
/etc/service/xvfb/run &
/etc/service/webswing/run