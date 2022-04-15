$ ->
# Requests a web socket from the server for two-way fully duplex communication
  ws = new WebSocket $("#home").data("ws-url")
  # On receiving a message, checks the response type and renders data accordingly
  console.log("here i am at socket creation")
  ws.onmessage = (event) ->
    message = JSON.parse event.data
    switch message.responseType
      when "searchResults"
        composeSearchResult(message.searchMap)
      when "skillsDetails"
        composeSkillResult(message)
      when "userDetails"
        composeUserResult(message)
      when "statsResults"
        ComposeStatistics(message)


  $("#searchForm").submit (event) ->
    event.preventDefault()
    phrase = $("#query").val()
    phrase = "4103952"
    if phrase == ""
      alert "search cant be empty"
      return false
    else
      ws.send(JSON.stringify({userId: phrase }))
      $("#phrase").val("")
      return

  $("#page-result").on "click", "a.gstats", (event) ->
      event.preventDefault()
      ws.send(JSON.stringify({statsQuery:  $(this).attr("id")}))
      return
  $("#page-result").on "click", "a.userdetails", (event) ->
        event.preventDefault()
        ws.send(JSON.stringify({userId:  $(this).attr("query")}))
        return
  $("#page-result").on "click", "a.jobdetails", (event) ->
        event.preventDefault()
        ws.send(JSON.stringify({jobId:  $(this).attr("id")}))
        return
  $("#page-result").on "click", "a.stats", (event) ->
        event.preventDefault()
        ws.send(JSON.stringify({description:  $(this).attr("query")}))
        return

  composeSearchResult = (searchMap) ->
    $("#page-result").empty()
    for canva in searchMap
      alert "in"
      $("#page-result").append(canva.title + "&nbsp" +canva.averageIndex+"&nbsp")
      stats = $("<a>").text("Gobal Stats").attr("class","gstats").attr("query",canva.title)
      $("#page-result").append(stats+"<br>")
      for p in canva.projects
        userlink = $("<a>").text(p.ownerID).attr("class", "userdetails").attr("id",p.ownerID)
        ustats = $("<a>").text("Stats").attr("class","stats").attr("query",p.desc)
        $("#page-result").append("Owner  " +userlink+"   title  "+p.title+"&nbsp<br>Index  "+p.readabilityIndex+"&nbsp<br>Descriptioon  "+p.desc+"&nbsp"+ustats+"<br>jobs  ")
        for j in p.skills
          joblink = $("<a>").text(j.job_name).attr("class","jobdetails").attr("id",j.job_id)
          $("#page-result").append(joblink+"&nbsp&nbsp&nbsp")

  ComposeStatistics =  (message) ->
      $("#page-result").empty()
      keys = message.stats.keys
      for key,value of message.stats
        if(typeof value == "object")
          $("#page-result").append "<br>    " + key + "    " + value + "  "

  composeSkillResult = (message) ->
      $("#page-result").empty()

      averageIndex = message.average
      projects = message.projects
      $("#page-result").append(averageIndex+"&nbsp")

      for p in projects
        userlink = $("<a>").text(p.ownerID).attr("class", "userdetails").attr("id",p.ownerID)
        ustats = $("<a>").text("Stats").attr("class","stats").attr("query",p.desc)
        $("#page-result").append("Owner  " +userlink+"   title  "+p.title+"&nbsp<br>Index  "+p.readabilityIndex+"&nbsp<br>Descriptioon  "+p.desc+"&nbsp"+ustats+"<br>jobs  ")
        for j in p.skills
          joblink = $("<a>").text(j.job_name).attr("class","jobdetails").attr("id",j.job_id)
          $("#page-result").append(joblink+"&nbsp&nbsp&nbsp")


