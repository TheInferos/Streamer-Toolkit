@echo off
echo Setting up environment variables...

if not exist .env (
    echo Creating .env file from template...
    copy env.example .env
    echo .env file created successfully!
) else (
    echo .env file already exists.
)

echo.
echo Environment setup complete!
echo You can now start the database with: docker-compose up -d
pause 