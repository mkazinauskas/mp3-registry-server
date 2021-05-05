#Sample request

`curl -XPOST -k acme:acmesecret@localhost:9999/uaa/oauth/token -d grant_type=password -d client_id=acme -d client_secret=acmesecret -d redirect_uri=http://google.com -d username=user -d password=password`