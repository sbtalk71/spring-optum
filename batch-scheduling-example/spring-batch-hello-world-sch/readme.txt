1. The application is a boot app
2. The automatic job launching is disabled through the application.properties file (see it)
3. A new class named ScheduledJob is created and Job Scheduling is configured. It is configured as spring bean.
4. When Spring boot application starts it also schedules the job and it triggers as per cron set.
5. for various cron setting please refer to cron documentation in UNIX or Linux OS and also spring scheduling.

Automatic Job launching must be disabled otherwise you will get exceptions

Notice, the job is lauched from the scheduled method whicg triggers automatically.

