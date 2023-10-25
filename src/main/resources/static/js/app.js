$(function ()
{
    $("a.confirmDeletion").click(function ()
    {
        if(!confirm("Do you want to delete?")) return false;
    });
});