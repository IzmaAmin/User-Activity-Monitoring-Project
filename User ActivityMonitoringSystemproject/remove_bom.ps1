$files = Get-ChildItem -Recurse -Include *.java
foreach ($f in $files) {
  $p = $f.FullName
  $t = [System.IO.File]::ReadAllText($p)
  if ($t.Length -gt 0 -and $t[0] -eq [char]0xFEFF) {
    $t = $t.Substring(1)
  }
  $enc = New-Object System.Text.UTF8Encoding($false)
  [System.IO.File]::WriteAllText($p, $t, $enc)
  Write-Host "Saved without BOM: $p"
}
