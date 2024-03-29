$siteandsvrur0Hardcoded = $args[0]
$siteandsvrur1Hardcoded = $args[1]



function siteandsvr(){
	$script:siteandsvrur1 = "q"
	do {
		$siteandsvrur0 =  $siteandsvrur0Hardcoded #Read-Host "Site (GA, ASH, STP, LTMA, LTMS, LAB, LOCAL)"
		
		$script:siteandsvrur1 = $siteandsvrur0.ToLower()
		if ($script:siteandsvrur1 -eq "ga") {
			$script:oct123 = "10.67.79."
		}
		if ($script:siteandsvrur1 -eq "ash") {
			$script:oct123 = "172.21.5."
		}
		if ($script:siteandsvrur1 -eq "stp") {
			$script:oct123 = "172.21.3."
		}
		if ($script:siteandsvrur1 -eq "ltma") {
			$script:oct123 = "161.254.200."
		}
		if ($script:siteandsvrur1 -eq "ltms") {
			$script:oct123 = "192.234.237."
		}
		if ($script:siteandsvrur1 -eq "lab") {
			$script:oct123 = "172.21.254."
		}
        
        if (($script:siteandsvrur1 -ne "ga") -and ($script:siteandsvrur1 -ne "ash") -and ($script:siteandsvrur1 -ne "stp") -and ($script:siteandsvrur1 -ne "ltma") -and ($script:siteandsvrur1 -ne "ltms") -and ($script:siteandsvrur1 -ne "lab") -and ($script:siteandsvrur1 -ne "local")) {
			Write-Host " "
			Write-Host "Valid options for Site are GA, ASH, STP, LTMA, LTMS, LAB, or LOCAL"
			Write-Host " "
		}
    }
    while (($script:siteandsvrur1 -ne "ga") -and ($script:siteandsvrur1 -ne "ash") -and ($script:siteandsvrur1 -ne "stp") -and ($script:siteandsvrur1 -ne "ltma") -and ($script:siteandsvrur1 -ne "ltms") -and ($script:siteandsvrur1 -ne "lab") -and ($script:siteandsvrur1 -ne "local"))  

        
    do {        
		if (($script:siteandsvrur1 -eq "ga") -or ($script:siteandsvrur1 -eq "ash") -or ($script:siteandsvrur1 -eq "stp")) {
            Write-Host " "
         	$script:siteandsvrur2 =  $siteandsvrur1Hardcoded               #Read-Host "Server (30..35 or )"
		}
        if ((($script:siteandsvrur1 -eq "ga") -or ($script:siteandsvrur1 -eq "ash") -or ($script:siteandsvrur1 -eq "stp")) -and ($script:siteandsvrur2 -ne "30") -and ($script:siteandsvrur2 -ne "31") -and ($script:siteandsvrur2 -ne "32") -and ($script:siteandsvrur2 -ne "33") -and ($script:siteandsvrur2 -ne "34") -and ($script:siteandsvrur2 -ne "35")) {
			Write-Host "Valid choices for STP and ASH Servers are 30, 31, 32, 33, 34, or 35"
			Write-Host " "
        }           
	}
	while ((($script:siteandsvrur1 -eq "ga") -or ($script:siteandsvrur1 -eq "ash") -or ($script:siteandsvrur1 -eq "stp")) -and ($script:siteandsvrur2 -ne "30") -and ($script:siteandsvrur2 -ne "31") -and ($script:siteandsvrur2 -ne "32") -and ($script:siteandsvrur2 -ne "33") -and ($script:siteandsvrur2 -ne "34") -and ($script:siteandsvrur2 -ne "35"))


    do {        
		if ($script:siteandsvrur1 -eq "lab") {
            Write-Host " "
            Write-Host "NOTE: (01-02 = EL01-02) and (01-02 = EM01-02)"
            Write-Host " "
			$script:siteandsvrur2 = Read-Host "Server (40, 41, 42, 43, 44, or 45)"
		}
        if (($script:siteandsvrur1 -eq "lab") -and ($script:siteandsvrur2 -ne "40") -and ($script:siteandsvrur2 -ne "41") -and ($script:siteandsvrur2 -ne "42") -and ($script:siteandsvrur2 -ne "43") -and ($script:siteandsvrur2 -ne "44") -and ($script:siteandsvrur2 -ne "45")) {
			Write-Host "Valid choices for LAB Server are 40, 41, 42, 43, 44, or 45"
            Write-Host " "
        }           
	}
	while (($script:siteandsvrur1 -eq "lab") -and ($script:siteandsvrur2 -ne "40") -and ($script:siteandsvrur2 -ne "41") -and ($script:siteandsvrur2 -ne "42") -and ($script:siteandsvrur2 -ne "43") -and ($script:siteandsvrur2 -ne "44") -and ($script:siteandsvrur2 -ne "45"))  
}


function yorn(){
	$script:yornur1 = "Y"	
}

$hsndns = "beauty.hsn.com","crafts-sewing.hsn.com","css.hsn.com","electronics.hsn.com","fashion.hsn.com","health-fitness.hsn.com","home-decor.hsn.com","home-solutions.hsn.com","hsn2.hsn.com","jewelry.hsn.com","js.hsn.com","kitchen-dining.hsn.com","sale.hsn.com","shoes-handbags.hsn.com","sports.hsn.com","toys.hsn.com","www.hsn.com","m.hsn.com"
$hsnlabdns = "beauty.hsnlab.com","beautyload.hsnlab.com","crafts-sewing.hsnlab.com","crafts-sewingload.hsnlab.com","css.hsnlab.com","cssload.hsnlab.com","electronics.hsnlab.com","electronicsload.hsnlab.com","fashion.hsnlab.com","fashionload.hsnlab.com","health-fitness.hsnlab.com","health-fitnessload.hsnlab.com","home-decor.hsnlab.com","home-decorload.hsnlab.com","home-solutions.hsnlab.com","home-solutionsload.hsnlab.com","jewelry.hsnlab.com","jewelryload.hsnlab.com","js.hsnlab.com","jsload.hsnlab.com","kitchen-dining.hsnlab.com","kitchen-diningload.hsnlab.com","sale.hsnlab.com","saleload.hsnlab.com","shoes-handbagslab.hsn.com","shoes-handbagsload.hsnlab.com","sports.hsnlab.com","sportsload.hsnlab.com","toys.hsnlab.com","toysload.hsnlab.com","www.hsnlab.com","wwwload.hsnlab.com","m.hsnlab.com"
$hostfilepath = "C:\Windows\system32\drivers\etc\"
$shop = "0"

Write-Host "Would you like to backup your hosts file?"
yorn
if ($script:yornur1 -eq "y") {
	$datetime = Get-Date -Format yyyyMMddhhmmss
	Move-Item "$hostfilepath\hosts" "$hostfilepath\hosts-$datetime"
	Write-Host "Hosts file saved to $hostfilepath\hosts-$datetime"
}
if ($script:yornur1 -eq "n") {
	Remove-Item "$hostfilepath\hosts"
}
$script:yornur1 = "q"

do {
	$hostsexist = Test-Path "$hostfilepath\hosts"
	if ($hostsexist -eq "True"){
		Remove-Item "$hostfilepath\hosts"
	}
	Write-Host "What Site and Server would you like for your hostfile?"
	siteandsvr
	Write-Host " "

	if ($script:siteandsvrur1 -eq "ga") {
		foreach ($dnsentry in $hsndns) {
			Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$script:siteandsvrur2 $dnsentry"
		}

        $shop = "39"
        Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$shop content.hsn.com"
        $shop = "100"
        Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$shop cnt.hsn.com"
        $shop = "1"
        Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$shop$script:siteandsvrur2 shop.hsn.com"
    }    

	if ($script:siteandsvrur1 -eq "ash") {
		foreach ($dnsentry in $hsndns) {
			Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$script:siteandsvrur2 $dnsentry"
		}

        $shop = "39"
        Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$shop content.hsn.com"
        $shop = "60"
        Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$shop forums.hsn.com"
        $shop = "100"
        Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$shop cnt.hsn.com"
        $shop = "1"
        Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$shop$script:siteandsvrur2 shop.hsn.com"
        $shop = "150"
        Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$shop blogs.hsn.com"

        Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "127.21.2.219 media.hsn.com"
    }    
    
    
    if ($script:siteandsvrur1 -eq "stp") {
		foreach ($dnsentry in $hsndns) {
			Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$script:siteandsvrur2 $dnsentry"
		}
        
        $shop = "39"
        Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$shop content.hsn.com"
        $shop = "60"
        Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$shop forums.hsn.com"
        $shop = "100"
        Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$shop cnt.hsn.com"
        $shop = "1"
        Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$shop$script:siteandsvrur2 shop.hsn.com"
        $shop = "150"
        Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$shop blogs.hsn.com"

        Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "127.21.2.219 media.hsn.com"

	}    
    
    
	if (($script:siteandsvrur1 -eq "ltma") -or ($script:siteandsvrur1 -eq "ltms")) {
        Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "127.21.2.219 media.hsn.com"
        $shop = "39"
        Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$shop content.hsn.com"
        $shop = "60"
        Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$shop forums.hsn.com"
        $shop = "100"
        Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$shop cnt.hsn.com"
        $shop = "150"
        Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$shop blogs.hsn.com"


		foreach ($dnsentry in $hsndns) {
            $shop = "220"
			Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$shop $dnsentry"
		}
        
		$shop = "221"
        Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$shop shop.hsn.com"
	}

    
	if ($script:siteandsvrur1 -eq "lab") {
		foreach ($dnsentry in $hsnlabdns) {
			Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$script:siteandsvrur2 $dnsentry"
		}
		$shop = "1"
        Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$shop$script:siteandsvrur2 shop.hsnlab.com"
		Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "$script:oct123$shop$script:siteandsvrur2 shopload.hsnlab.com"        
	}


	if ($script:siteandsvrur1 -eq "local") {
		Out-File -Append -FilePath $hostfilepath\hosts -Encoding ASCII -InputObject "127.0.0.1 localhost"
	}

	Write-Host "Your new hosts file has been created"
	Write-Host " "
	Write-Host "Would you like to edit your hosts file again?"
	yorn
	Write-Host " "
}
while (1 -ne 1)