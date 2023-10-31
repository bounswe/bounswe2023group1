import * as React from 'react';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import FileDownloadIcon from '@mui/icons-material/FileDownload';
import Button from '@mui/material/Button';

function ImageUploadComponent({dragging, droppedImage, handleDragOver, handleDragLeave, handleDrop, handleUploadId}) {
    return (
        <Box
            onDragOver={handleDragOver}
            onDragLeave={handleDragLeave}
            onDrop={handleDrop}
            sx={{
                mt: 2,
                border: dragging ? '4px dashed #FF0000' : '2px dashed #E0E0E0',
                borderRadius: '8px',
                padding: '64px',
                textAlign: 'center',
                width: '100%',
                minHeight: '300px',
                minWidth: '450px',
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
            }}
        >
            {dragging ? (
                <Typography variant="body1" sx={{textAlign: 'center'}}>
                    Drop the image here
                </Typography>
            ) : (
                droppedImage ? (
                    // eslint-disable-next-line jsx-a11y/img-redundant-alt
                    <img
                        src={droppedImage}
                        alt="Dropped Image"
                        style={{maxWidth: '100%', maxHeight: '100%'}}
                    />
                ) : (
                    <>
                        <div>
                            <FileDownloadIcon
                                sx={{fontSize: 100}}
                                style={{
                                    color: 'rgba(169, 169, 169, 1)',
                                    padding: '12px',
                                    borderRadius: '8px',
                                }}
                            />
                        </div>
                        <div>
                            <div>
                                <Typography variant="body1" sx={{
                                    textAlign: 'center',
                                    fontWeight: 'bold',
                                    color: 'rgba(169, 169, 169, 1)'
                                }}>
                                    DRAG AN IMAGE HERE
                                </Typography>
                            </div>
                            <div>
                                <Typography variant="body1" sx={{
                                    textAlign: 'center',
                                    fontWeight: 'bold',
                                    color: 'rgba(169, 169, 169, 1)'
                                }}>
                                    OR
                                </Typography>
                            </div>
                        </div>
                    </>
                )
            )}
            <Button
                variant="contained"
                component="label"
                style={{
                    backgroundColor: 'rgba(169, 169, 169, 0.7)',
                    boxShadow: '0px 4px 8px rgba(0, 0, 0, 0.2)',
                    padding: '12px',
                    borderRadius: '8px',
                    marginTop: '12px',
                }}
            >
                Upload a file
                <input type="file" hidden onChange={handleUploadId}/>
            </Button>
        </Box>
    );
}

export default ImageUploadComponent;
