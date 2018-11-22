

//------------------------- READ ME --------------------------//


[Project Name]

Ballot Application



[How to run]

1. Unzip file

2. Compile it

3. Use "Spring Tools Suite" or "Command Line" to run it

4. Open browser and type "https://localhost:8090" in address bar


[Instructions]

****************
IMPORTANT:

You can check both [Browser Console] and [CommandLine/STS Console] to see what's going on behind the webpage.

****************


For Voter Account:

(Login Part)
- Log in using voter email and password (use provided one below or create by self).
- You can choose "Remember Login Credentials" to save your email and password in Cookie.
- If login success, you will be redirected to Voter Home.
- Else an error message will slide down and tell you the reason (Althrough normally for security reason, you can't be notified what exactly was going wrong, but for the requirement of [Detailed error messages  (e.g. 'Login failed: incorrect password')] in this coursework, you will be able to know the detail).
- In Voter Home you can see your personal info (email, birthday, address, voted item).
- If you are eligible to vote, the "Vote Now" button will be unlocked for you automatically.
- In vote page, you can choose any option (ONLY ONE) and submit (if vote is opened by admin, default is open)
- After vote you will be redirect back to voter home page and your voted item can be seen
- You can log out now.
- If you chose "Remember Login Credentials" before login, you will see your email and password has been auto-filled for you.

(Signup Part)
- Filling all the blank fields.
- The webpage will auto-check your input.
- The availability of your Email input will be auto-checked with database (if pattern is correct).
- Your input password will be checked and Password Meter will show its strength.
- The availability of your BIC Code input will be auto-checked with database (if pattern is correct).
- Once all fields are valid (all "OK") the submit button will be unlocked automatically.
- You can clear ALL your input by click "Reset".
- Once submit, you will be redirect to Login page.


For Admin Account:

- Log in using admin email and password (you can only use the provided one below).
- You can choose "Remember Login Credentials" to save your email and password in Cookie.
- If login success, you will be redirected to Admin Dashboard.
- Else an *Detailed* error message will slide down (Explained before).
- In Admin Dashboard you can see the pre-defined Vote Question and Options.
- You are able to close the vote by click "Close" button next to the status if vote rate > 80%.
- Otherwise, you can't change it and an alert box will pop out. (at this time you can see current registered users and counted votes in [CommandLine/STS Console]).
- Once vote is closed, you can change the title of question and content of options.
- To check vote data, go to "Statistics", you may need to scroll down the page.
- By clicking "View Results" button, a table with current vote data will be generated.
- To check different charts, you can click "View Pie Chart" and "View Bar Chart".
- To update data, click "Hide Results" and click "View Results" to show it again.
- You can log out now.
- If you chose "Remember Login Credentials" before login, you will see your email and password has been auto-filled for you.








[Accounts]

Admin Account (Pre-defined)

-Email

	admin@gov.com


-Password

	Admin123

------------------------------------

Voter Account (Pre-defined)

-Email

	voter@gov.com


-Password

	Voter123



[BIC Code List]
7F63-YDNH-G2LL-AKY7
7M73-LMDA-883S-EJT7
9T5C-RD3T-RYF2-SSJM
DJL8-3RDP-32JS-QUA8
E4U4-Z87Z-G6BM-QEJM
EHNL-G55E-YMQD-GF25
GJ2X-ERGA-RGT7-D9V9
TGJ7-CHX4-8FXA-35WF
VLPF-MXSF-533T-BA5Y
ZCXT-G275-JJ3R-C5YU