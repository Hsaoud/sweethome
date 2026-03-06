<#
.SYNOPSIS
    Starts the Sweet Home backend and frontend applications idempotently.
.DESCRIPTION
    This script finds and kills any existing processes listening on the backend port (8080)
    and the frontend port (4200). It then launches both applications in separate processes.
#>

$ErrorActionPreference = "Stop"

$BackendPort = 8080
$FrontendPort = 4200

function Kill-ProcessOnPort {
    param ([int]$Port)
    Write-Host "Checking for existing processes on port $Port..." -ForegroundColor Cyan
    
    # Get TCP connections listening on the specific port
    $connections = Get-NetTCPConnection -LocalPort $Port -State Listen -ErrorAction SilentlyContinue
    
    if ($connections) {
        foreach ($conn in $connections) {
            $pidToKill = $conn.OwningProcess
            if ($pidToKill) {
                Write-Host "Found process $pidToKill listening on port $Port. Killing it..." -ForegroundColor Yellow
                Stop-Process -Id $pidToKill -Force -ErrorAction SilentlyContinue
                Write-Host "Process $pidToKill terminated." -ForegroundColor Green
            }
        }
    } else {
        Write-Host "No process found on port $Port." -ForegroundColor Green
    }
}

# 1. Kill existing processes
Kill-ProcessOnPort -Port $BackendPort
Kill-ProcessOnPort -Port $FrontendPort

# Add a slight delay to ensure ports are freed by the OS
Start-Sleep -Seconds 2

# 2. Start the Backend
Write-Host "Starting Sweet Home Backend (Spring Boot)..." -ForegroundColor Cyan
$BackendPath = Join-Path -Path $PSScriptRoot -ChildPath "sweethome-back"
if (Test-Path $BackendPath) {
    Set-Location -Path $BackendPath
    # Using Start-Process to run in a new Windows terminal window
    Start-Process -FilePath "cmd.exe" -ArgumentList "/k .\mvnw.cmd spring-boot:run" -WorkingDirectory $BackendPath
    Write-Host "Backend started in a new window." -ForegroundColor Green
} else {
    Write-Host "Backend directory not found at $BackendPath" -ForegroundColor Red
}

# 3. Start the Frontend
Write-Host "Starting Sweet Home Frontend (Angular)..." -ForegroundColor Cyan
$FrontendPath = Join-Path -Path $PSScriptRoot -ChildPath "sweethome-front"
if (Test-Path $FrontendPath) {
    Set-Location -Path $FrontendPath
    # Using Start-Process to run in a new Windows terminal window
    Start-Process -FilePath "cmd.exe" -ArgumentList "/k npm start" -WorkingDirectory $FrontendPath
    Write-Host "Frontend started in a new window." -ForegroundColor Green
} else {
    Write-Host "Frontend directory not found at $FrontendPath" -ForegroundColor Red
}

# Return to root directory
Set-Location -Path $PSScriptRoot
Write-Host "Launch sequence complete!" -ForegroundColor Green
