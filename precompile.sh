# require('roy', 'coffeescript')
#find war/public/app/scripts -name \*.roy -exec roy  {} \;

find war/public/app/scripts -name \*.coffee -exec coffee -c  {} \;