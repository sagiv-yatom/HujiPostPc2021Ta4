I pledge the highest level of ethical principles in support of academic excellence.
I ensure that all of my work reflects my own abilities and not those of someone else.

Question:
Testing the CalculateRootsService for good input is pretty easy - we pass in a number and we expect a broadcast intent with the roots.
Testing for big prime numbers can be frustrating - currently the service is hard-coded to run for 20 seconds before giving up, which would make the tests run for too long.

What would you change in the code in order to let the service run for maximum 200ms in tests environments, but continue to run for 20sec max in the real app (production environment)?

Answer:
In buttonCalculateRoots.setOnClickListener (in MainActivity file), I would check if I am in release mode or in debug mode
using "if(BuildConfig.DEBUG)".
- If it is true (debug mode) I would add to intentToOpenService an extra parameter of running time
    that equals to 200 seconds.
- otherwise (release time) I would add to intentToOpenService an extra parameter of running time
    that equals to 20 seconds.

In onHandleIntent (in CalculateRootsService file), I would check the above extra parameter that I've got
with the intent and define endTime variable accordingly.