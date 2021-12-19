import requests
from bs4 import BeautifulSoup

base_url = "https://www.imdb.com/search/title/?genres=#GENRE#&title_type=feature"


def extract(url, genre):
    movies = []
    page = requests.get(url)
    soup = BeautifulSoup(page.content, "html.parser")

    items = soup.find_all("div", class_="lister-item")
    links = []
    for item in items:
        link = item.find("h3", class_="lister-item-header").find("a")["href"]
        links.append(f"https://www.imdb.com{link}")
    for link in links:
        movie = {"genre": genre}
        page = requests.get(link)
        soup = BeautifulSoup(page.content, "html.parser")
        title = soup.find("h1", {"data-testid": "hero-title-block__title"}).text
        movie["title"] = title
        li_list = soup.find("ul", {"data-testid": "hero-title-block__metadata"}).findAll("li")
        if len(li_list) == 3:
            movie["release_year"] = li_list[0].find("span").text
            movie["movie_rating"] = li_list[1].find("span").text
            movie["run_time"] = li_list[2].text
            try:
                movie["imdb_rating"] = soup.find("span", class_="AggregateRatingButton__RatingScore-sc-1ll29m0-1").text
            except Exception as e:
                print(e)
            movie["description"] = soup.find("span", class_="GenresAndPlot__TextContainerBreakpointXL-cum89p-2").text
            image_link = soup.find("div", {"data-testid": "hero-media__poster"}).find("a")["href"]
            directors_li = soup.findAll("li", {"data-testid": "title-pc-principal-credit"})[0].findAll("li")
            directors = []
            for director_li in directors_li:
                directors.append(fix_name(director_li.text))
            movie["directors"] = directors
            actors_li = soup.findAll("li", {"data-testid": "title-pc-principal-credit"})[2].findAll("li")
            actors = []
            for actor_li in actors_li:
                actors.append(fix_name(actor_li.text))
            movie["actors"] = actors

            page = requests.get(f"https://www.imdb.com{image_link}")
            soup = BeautifulSoup(page.content, "html.parser")
            image_url = soup.findAll("img", class_="MediaViewerImagestyles__PortraitImage-sc-1qk433p-0")[0]["src"]
            movie["image_url"] = image_url
            print(movie)
            requests.post("http://movieapp.ddns.net:8080/api/load/movie", json=movie)
    print(movies)


def fix_name(name):
    if "(" in name:
        name = name.split("(")[0]
    return name


genres = ["animation", "action", "horror", "superhero", "adventure"]
for genre in genres:
    # genre = "animation"
    animation_url = base_url.replace("#GENRE#", genre)
    extract(animation_url, genre)
