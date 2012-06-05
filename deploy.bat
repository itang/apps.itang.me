@echo off
lein clean && lein appengine-prepare && appcfg update war/
