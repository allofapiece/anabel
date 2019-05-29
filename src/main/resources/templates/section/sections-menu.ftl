<nav id="sections-menu" class="core-nav nav-core-fixed nav-sidebar">
    <div class="nav-header">
        <a href="#" class="brand">
            All sections
        </a>
        <button class="toggle-bar">
            <i data-feather="menu"></i>
        </button>
    </div>
    <ul class="menu">
        <#list menuSections as menuSection>
            <li>
                <a href="#">
                    <i data-feather="underline"></i> ${menuSection.name}
                </a>
            </li>
        </#list>
    </ul>

    <ul class="attributes">
        <li>
            <a href="#">
                <i class="icon" data-feather="facebook"></i>
            </a>
        </li>
        <li>
            <a href="#">
                <i class="icon" data-feather="twitter"></i>
            </a>
        </li>
        <li>
            <a href="#">
                <i class="icon" data-feather="instagram"></i>
            </a>
        </li>
    </ul>
</nav>
<#--
<nav id="my-menu">
    <ul>
        <li class="active"><a href="/">Home</a></li>
        <li><span>About us</span>
            <ul>
                <li><a href="/about/history/">History</a></li>
                <li><a href="/about/team/">The team</a></li>
                <li><a href="/about/address/">Our address</a></li>
            </ul>
        </li>
        <li><a href="/contact/">Contact</a></li>
    </ul>
</nav>
-->
