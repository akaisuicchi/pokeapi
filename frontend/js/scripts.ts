import Turbolinks from 'turbolinks';

Turbolinks.start();

document.addEventListener('turbolinks:load', () => {
    pagePokedexScripts();
})

const pagePokedexScripts = () => {
    const more = document.querySelector('#more') as HTMLButtonElement;
    const pokedex = document.querySelector('#pokedex') as HTMLDivElement;
    const limit = document.querySelector('#limit') as HTMLSelectElement;

    if (limit) {
        limit.addEventListener('change', () => {
            const offset = offsetValue();
            const limit = limitValue();
            const path = `?offset=${offset}&limit=${limit}`
            updateUrlState(path)
        })
    }

    if (more) {
        // @ts-ignore
        more.addEventListener('click', async () => {
            more.disabled = true;

            const location = window.location;
            const offset = offsetValue() + limitValue();
            const limit = limitValue();
            const path = `?offset=${offset}&limit=${limit}`

            try {
                await fetch(`${location.href}${path}&fragment`, {method: 'GET', headers: {'Content-type': 'text/html'}})
                    .then(response => response.text())
                    .then(appendPokedexResult)
                    .then(() => updateUrlState(path))
                    .catch(console.error)
            } catch (e) {
                alert('Could not load more items')
            } finally {
                more.disabled = false;
            }
        })
    }

    const limitValue = (): number => {
        if (!limit) {
            return 0;
        }

        return +limit.value || 0;
    }

    const offsetValue = (): number => {
        const search = new URLSearchParams(window.location.search);
        return +search.get('offset') || 0;
    }

    const appendPokedexResult = (response: string) => {
        if (!pokedex) {
            return;
        }

        const fragment = new DocumentFragment();
        const parser = new DOMParser();
        const html = parser.parseFromString(response as string, 'text/html');

        // @ts-ignore
        Array.from(html.body.querySelectorAll('a')).forEach(el => fragment.appendChild(el));
        pokedex.appendChild(fragment);
    }

    const updateUrlState = (path: string) => {
        window.history.pushState(null, null, path)
    }
}