import axios from 'axios';

export const ANNO_BASE_URL = 'https://annotation.resq.org.tr/annotations'

export async function postAnno(annotation) {
    await axios.post(`${ANNO_BASE_URL}/`, JSON.stringify(annotation), {
        headers: {
            'Content-Type': 'text/plain',
        }
    });
}

export async function editAnno(annotation) {
    await axios.put(annotation.id, annotation);
}

export async function getAllAnnos() {
    const {data} = await axios.get(`${ANNO_BASE_URL}/`);
    return data.map(({value})=>JSON.parse(value))
}

export async function deleteAnno(annotation) {
    await axios.delete(annotation.id);
}
