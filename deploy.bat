@echo off
lein1 clean && lein1 appengine-prepare && appcfg update war/
