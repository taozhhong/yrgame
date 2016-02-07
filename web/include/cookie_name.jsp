<%
    Cookie[] name1=request.getCookies();
    String cookie_game_value="";
    if(name1!=null)
    {
        for(int i=0;i<name1.length;i++)
        {
            Cookie c=name1[i];
            if(c.getName().equals("537u_Cookie"))
            {
                cookie_game_value=tostr.decrypt(c.getValue());
                break;

            }
            else
            {
                cookie_game_value="";
            }
        }
    }
%>