import React, {useState} from 'react'
import axios from 'axios';
import './WikidataPage.css';
import {Button, Form} from 'react-bootstrap';

export default function WikidataPage() {

    //Directions

    const [query, setQuery] = useState("");
    const [searchEntity, setSearchEntity] = useState({id: "", name: ""});

    const [searchEntityCategories, setSearchEntityCategories] = useState([]);

    const [savedEntities, setSavedEntities] = useState([]);

    const findEntity = () => {
        axios.get(`/api/category/findEntity?query=${query}`)
            .then((res) => res.data)
            .then(data => setSearchEntity(data))
            .catch(e => console.log(e))
    }

    const getCategories = () => {
        axios.get(`/api/category/getCategoriesOf?id=${searchEntity.id}`)
            .then((res) => res.data)
            .then(data => setSearchEntityCategories(data))
            .catch(e => console.log(e))
    }

    const getSavedEntities = () => {
        axios.get(`/api/category/getEntities`)
            .then((res) => res.data)
            .then(data => setSavedEntities(data))
            .catch(e => console.log(e))
    }

    const saveEntity = () => {
        axios.post(`/api/category/saveEntity?id=${searchEntity.id}`)
            .then((res) => res.data)
            .then(data => setSavedEntities(data))
            .catch(e => console.log(e))
    }


    return (<div className='directionsDiv'>
        <Form className="formDiv">
            <Form.Group className="mb-3" controlId="from">
                <Form.Label>Name of Entity</Form.Label>
                <Form.Control type="text" placeholder="Enter entity name" onChange={(e) => setQuery(e.target.value)}/>
            </Form.Group>

            <Button variant="primary" onClick={findEntity}>
                Search
            </Button>

            {searchEntity.id && <>
                <br/>
                {searchEntity.id}: {searchEntity.name} <br/>
                <Button variant="success" onClick={getCategories} style={{marginLeft: "10px"}}>
                    Find Categories
                </Button>
                <Button variant="success" onClick={saveEntity} style={{marginLeft: "10px"}}>
                    Save Entity
                </Button>
            </>}
        </Form>
        {searchEntityCategories && <div className='directionsDiv'>
            {searchEntityCategories.map(cat => cat.name).join(", ")}
        </div>}
        <Button variant="success" onClick={getSavedEntities} style={{marginLeft: "10px"}}>
            Get Saved Entities
        </Button>
        <div className='directionsDiv'>
            {savedEntities && savedEntities.map(e => <>{e.id}: {e.name} <br/></>)}
        </div>
    </div>)
}
